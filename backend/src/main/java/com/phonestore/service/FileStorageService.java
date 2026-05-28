package com.phonestore.service;

import com.phonestore.exception.BadRequestException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_EXT = Set.of("jpg", "jpeg", "png", "webp", "gif", "svg");
    private static final long MAX_SIZE = 5L * 1024 * 1024; // 5MB

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() throws IOException {
        uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File rỗng");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new BadRequestException("File vượt quá 5MB");
        }
        String original = StringUtils(file.getOriginalFilename());
        String ext = extOf(original);
        if (!ALLOWED_EXT.contains(ext)) {
            throw new BadRequestException("Chỉ chấp nhận: " + ALLOWED_EXT);
        }
        String filename = UUID.randomUUID() + "." + ext;
        try {
            Path target = uploadPath.resolve(filename);
            // Ngăn path traversal
            if (!target.getParent().equals(uploadPath)) {
                throw new BadRequestException("Tên file không hợp lệ");
            }
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi lưu file: " + e.getMessage(), e);
        }
    }

    private String extOf(String filename) {
        int dot = filename.lastIndexOf('.');
        return dot < 0 ? "" : filename.substring(dot + 1).toLowerCase(Locale.ROOT);
    }

    private String StringUtils(String name) {
        return name == null ? "" : name.replaceAll("[^A-Za-z0-9._-]", "_");
    }
}
