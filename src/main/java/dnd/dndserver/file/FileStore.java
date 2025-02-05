package dnd.dndserver.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    //    @Value("${file.dir}")

    private final String fileDir = "";

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<ImageFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ImageFile> storeFileResult = new ArrayList<>();
        for (int i = 0; i < multipartFiles.size(); i++) {
            MultipartFile multipartFile = multipartFiles.get(i);
            if (!multipartFile.isEmpty()) {
                // 파일 확장자 유효성 검사
                if (isValidImageFile(multipartFile)) {
                    storeFileResult.add(storeFile(multipartFile));
                } else {
                    // 확장자가 유효하지 않은 경우 에러 메시지 추가
                    throw new RuntimeException();
                }
            }
        }
        return storeFileResult;
    }


    public ImageFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new ImageFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private boolean isValidImageFile(MultipartFile file) {
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? extractExt(originalFilename) : null;
        return fileExtension != null && allowedExtensions.contains(fileExtension.toLowerCase());
    }
}