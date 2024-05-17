package dnd.dndserver.user;

import dnd.dndserver.article.dto.request.FindArticleRequest;
import dnd.dndserver.article.dto.response.FindAllArticleResponse;
import dnd.dndserver.global.handler.ResponseHandler;
import dnd.dndserver.user.application.UserService;
import dnd.dndserver.user.application.repository.UserRepository;
import dnd.dndserver.user.dto.UserLoginRequest;
import dnd.dndserver.user.dto.UserLoginResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseHandler<UserLoginResponse>> login(
            @RequestPart UserLoginRequest request,
            @RequestPart MultipartFile imageFile
    ) throws IOException {

        request.setImageFile(imageFile);

        return ResponseEntity.ok()
                .body(ResponseHandler.<UserLoginResponse>builder()
                        .statusCode(HttpStatus.OK)
                        .data(userService.login(request))
                        .build()
                );
    }
}
