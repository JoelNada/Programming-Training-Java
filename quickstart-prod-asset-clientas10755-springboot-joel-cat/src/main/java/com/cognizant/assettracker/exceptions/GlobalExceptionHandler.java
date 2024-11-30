package com.cognizant.assettracker.exceptions;

import com.cognizant.assettracker.models.ErrorResponse;
import com.cognizant.assettracker.models.ErrorDetails;
import com.cognizant.assettracker.models.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static java.lang.String.format;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(Exception exception,
                                                                 WebRequest request) {
        logger.error("Encountered AccessDeniedException ", exception);
        String errorMessage = format("Encountered exception %s", exception.getMessage());
        return handleExceptionInternal(exception,
                new ErrorResponse(errorMessage, HttpStatus.FORBIDDEN.value()), new HttpHeaders(),
                HttpStatus.FORBIDDEN, request);
    }
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleApiException(Exception exception, WebRequest request) {
        logger.error("Encountered an unhandled exception", exception);
        String errorMessage = format("Encountered exception %s", exception.getMessage());
        return handleExceptionInternal(exception,
                new ErrorDetails(new Date(),errorMessage),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public ResponseEntity<String> signatureException(io.jsonwebtoken.security.SignatureException e){
        return new ResponseEntity<>("JWT Signature Exception: "+e.getMessage(),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> ExpiredException(ExpiredJwtException e){
        return new ResponseEntity<>("JWT Token Expired! Please login again! ",HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(JWTException.class)
    public ResponseEntity<ErrorDetails> jwtExceptionHandler(JWTException e){
        return ResponseEntity.ok(new ErrorDetails(new Date(),e.getMessage()));
    }
    @ExceptionHandler(FileTooLargeException.class)
    public ResponseEntity<ErrorDetails>fileTooLarge(FileTooLargeException e){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),e.getMessage());
        return new ResponseEntity<>(errorDetails,HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ErrorDetails>userEmailExistsException(UserEmailExistsException e){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),e.getMessage());
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDetails>employeeNotFound(EmployeeNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoDocumentsException.class)
    public ResponseEntity<ErrorDetails> noDocuments(NoDocumentsException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(CommitedException.class)
    public ResponseEntity<ErrorDetails> committedResponse(CommitedException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(UnsupportedFileException.class)
    public ResponseEntity<ErrorDetails> unsupportedFile(UnsupportedFileException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginDetailException.class)
    public ResponseEntity<ErrorDetails> loginException(LoginDetailException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ErrorDetails> reportNotFoundException(ReportNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorDetails> DocumentNotFoundException(DocumentNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<ErrorDetails> assetNotFoundException(AssetNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorDetails> projectNotFoundException(ProjectNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EPLException.class)
    public ResponseEntity<ErrorDetails> eplException(EPLException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HomePageException.class)
    public ResponseEntity<ErrorDetails> homePageException(EPLException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MasterHistoryException.class)
    public ResponseEntity<ErrorDetails> masterHistoryException(MasterHistoryException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AssetReleaseSaveException.class)
    public ResponseEntity<ErrorDetails> assetReleaseSaveException(AssetReleaseSaveException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AdminApprovalException.class)
    public ResponseEntity<ErrorDetails> adminApprovalException(AdminApprovalException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<ErrorDetails> userEmailNotFoundException(UserEmailNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ExcelTemplateException.class)
    public ResponseEntity<ErrorDetails> excelTemplateException(ExcelTemplateException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(UnauthorizedRoleException.class)
    public ResponseEntity<ErrorDetails> unauthorizedRoleException(UnauthorizedRoleException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails>runtimeException(RuntimeException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> badCredentialsException(BadCredentialsException e ) {
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TrackingException.class)
    public ResponseEntity<ErrorDetails> trackingException(TrackingException e ) {
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(WrongChoiceException.class)
    public ResponseEntity<ErrorDetails> wrongChoiceException(WrongChoiceException e ) {
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotificationsException.class)
    public ResponseEntity<ErrorDetails> notificationException(NotificationsException e ) {
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TemplateException.class)
    public ResponseEntity<ErrorDetails> templateException(TemplateException e ) {
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorDetails> emailNotFoundException(EmailNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordResetException.class)
    public ResponseEntity<ErrorDetails> invalidPasswordResetException(InvalidPasswordResetException e){
        return new ResponseEntity<>(new ErrorDetails(new Date(),e.getMessage()),HttpStatus.NOT_FOUND);
    }


}
