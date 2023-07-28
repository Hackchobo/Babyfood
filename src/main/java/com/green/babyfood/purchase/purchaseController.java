package com.green.babyfood.purchase;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "구매페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
public class purchaseController {

}
