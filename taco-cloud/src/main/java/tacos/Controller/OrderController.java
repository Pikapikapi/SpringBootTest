package tacos.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.data.TacoOrderRepository;
import tacos.web.TacoOrder;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private TacoOrderRepository tacoOrderRepo;

    public OrderController(TacoOrderRepository tacoOrderRepo) {
        this.tacoOrderRepo = tacoOrderRepo;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        tacoOrderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
