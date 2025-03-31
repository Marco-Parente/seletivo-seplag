package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.FotoPessoa.FotoPessoa;
import com.example.demo.model.FotoPessoa.FotoPessoaMapper;
import com.example.demo.model.FotoPessoa.ObterFotoPessoaComLinkDTO;
import com.example.demo.repository.FotoPessoaRepository;
import com.example.demo.repository.PessoaRepository;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

@Service
public class FotoService {

    private final MinioClient minioClient;
    private final FotoPessoaRepository fotoPessoaRepository;
    private final PessoaRepository pessoaRepository;
    private final FotoPessoaMapper fotoPessoaMapper;

    @Value("${minio.bucket}")
    private String bucketName;

    public FotoService(MinioClient minioClient, FotoPessoaRepository fotoPessoaRepository,
            PessoaRepository pessoaRepository, FotoPessoaMapper fotoPessoaMapper) {
        this.minioClient = minioClient;
        this.fotoPessoaRepository = fotoPessoaRepository;
        this.pessoaRepository = pessoaRepository;
        this.fotoPessoaMapper = fotoPessoaMapper;
    }

    public FotoPessoa savePhoto(MultipartFile file, Integer pessoaId) throws Exception {
        var pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new NotFoundException(pessoaId));

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        String filename = UUID.randomUUID().toString();
        String contentType = file.getContentType();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filename)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(contentType)
                        .build());

        FotoPessoa fotoPessoa = new FotoPessoa();
        fotoPessoa.setPessoa(pessoa);
        fotoPessoa.setFpData(new Date());
        fotoPessoa.setFpBucket(bucketName);
        fotoPessoa.setFpHash(filename);

        return fotoPessoaRepository.save(fotoPessoa);
    }

    public List<ObterFotoPessoaComLinkDTO> getPessoaPhotos(Integer pessoaId) throws Exception {
        var fotosPessoa = fotoPessoaRepository.findByPessoa_PesId(pessoaId);

        var linksFotos = fotosPessoa.stream().map(
                foto -> {
                    try {
                        var link = minioClient.getPresignedObjectUrl(
                                GetPresignedObjectUrlArgs.builder()
                                        .method(Method.GET)
                                        .bucket(foto.getFpBucket())
                                        .object(foto.getFpHash())
                                        .expiry(5, TimeUnit.MINUTES)
                                        .build());

                        var dto = fotoPessoaMapper.toDTO(foto, link);

                        return dto;
                    } catch (Exception e) {
                        throw new RuntimeException("Error generating presigned URL", e);
                    }
                }).collect(Collectors.toList());

        return linksFotos;
    }

    public void deletePhoto(Integer fotoPessoaId) {
        fotoPessoaRepository.findById(fotoPessoaId).stream().forEach((f) -> {
            try {

                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(f.getFpBucket())
                                .object(f.getFpHash())
                                .build());

            } catch (Exception e) {
                // Logar o erro se fosse produção
            }

            fotoPessoaRepository.delete(f);
        });

    }
}
