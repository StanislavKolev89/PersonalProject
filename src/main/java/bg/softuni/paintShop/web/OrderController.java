package bg.softuni.paintShop.web;


import bg.softuni.paintShop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

}

