package aiss.gitminer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason="Project not found")
public class ProjectNotFoundException extends Throwable {
}
