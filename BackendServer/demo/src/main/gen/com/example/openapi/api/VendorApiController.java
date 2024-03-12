package com.example.openapi.api;

import com.example.openapi.model.FoodItemFull;
import com.example.openapi.model.LoginRequest;
import com.example.openapi.model.VendorAddProductRequest;
import com.example.openapi.model.VendorChangeProductAvailabileRequest;
import com.example.openapi.model.VendorDetails;
import com.example.openapi.model.VendorEditProductRequest;
import com.example.openapi.model.VendorGetRequestedOrders200ResponseInner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T04:08:19.693566868+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("${openapi.fDS.base-path:}")
public class VendorApiController implements VendorApi {

    private final NativeWebRequest request;

    @Autowired
    public VendorApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
