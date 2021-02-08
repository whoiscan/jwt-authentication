package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.zhama.jwtsecurity.models.JsonSend;
import uz.zhama.jwtsecurity.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/create")
    public JsonSend createInvoice() {
        return invoiceService.createInvoice();
    }
}
