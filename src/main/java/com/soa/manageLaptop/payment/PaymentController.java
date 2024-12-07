package com.soa.manageLaptop.payment;

import com.soa.manageLaptop.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/vn-pay")
    public ApiResponse<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return ApiResponse.<PaymentDTO.VNPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("OK")
                .result(paymentService.createVnPayPayment(request))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public RedirectView payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            paymentService.payCallbackHandler(request);
//              return ApiResponse.<PaymentDTO.VNPayResponse>builder()
//                      .code(HttpStatus.OK.value())
//                      .message("OK")
//                      .result(paymentService.payCallbackHandler(request))
//                      .build();
            return new RedirectView("/payment-success");
        } else {
//            return ApiResponse.<PaymentDTO.VNPayResponse>builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message("Bad Request")
//                    .build();
            return new RedirectView("/payment-failure");
        }
    }
}
