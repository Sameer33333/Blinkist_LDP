package com.training.blinkist.exceptions;


import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    public class ErrorResponse {

        private HttpStatus httpStatus;
        private Date timestamp;
        private List<String> errorMessages;

        public ErrorResponse(HttpStatus httpStatus, List<String> validationFailures,Date timestamp) {
            errorMessages=new ArrayList<>();
            for(var string : validationFailures){
                errorMessages.add(string);
            }
            this.httpStatus=httpStatus;
            this.timestamp=timestamp;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public List<String> getErrorMessages() {
            return errorMessages;
        }

        public void setErrorMessages(List<String> errorMessages) {
            this.errorMessages = errorMessages;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }


    }

