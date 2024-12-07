package com.soa.manageLaptop.payment;

import com.soa.manageLaptop.configuration.VNPAYConfig;
import com.soa.manageLaptop.model.Order;
import com.soa.manageLaptop.model.VNPayUtil;
import com.soa.manageLaptop.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;

    public static String orderId;

    public static BigDecimal amount_static;

    private final OrderRepository orderRepository;



    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        amount_static = BigDecimal.valueOf(amount / 100);
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        String bankNumber = request.getParameter("bankNumber");
        vnpParamsMap.put("vnp_OrderInfo",bankNumber);
        orderId = bankNumber;
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }


    public PaymentDTO.VNPayResponse payCallbackHandler(HttpServletRequest request) {
        Order order = orderRepository.getById(Long.valueOf(orderId));
        order.setStatus("Đã thanh toán");
        orderRepository.save(order);
        return new PaymentDTO.VNPayResponse(String.valueOf(HttpStatus.OK.value()),"success","OK");
    }



}
