package uz.zhama.jwtsecurity.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.zhama.jwtsecurity.entity.Cart;
import uz.zhama.jwtsecurity.entity.CartItems;
import uz.zhama.jwtsecurity.entity.Invoice;
import uz.zhama.jwtsecurity.models.InvoiceResponse;
import uz.zhama.jwtsecurity.models.JsonSend;
import uz.zhama.jwtsecurity.repository.CartRepository;
import uz.zhama.jwtsecurity.repository.Cart_itemsRepository;
import uz.zhama.jwtsecurity.repository.InvoiceRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final CartRepository cartRepository;
    private final Cart_itemsRepository cart_itemsRepository;
    private final InvoiceRepository invoiceRepository;
    private final SecurityUtil securityUtil;

    @Autowired
    public InvoiceServiceImpl(CartRepository cartRepository, Cart_itemsRepository cart_itemsRepository, InvoiceRepository invoiceRepository, SecurityUtil securityUtil) {
        this.cartRepository = cartRepository;
        this.cart_itemsRepository = cart_itemsRepository;
        this.invoiceRepository = invoiceRepository;
        this.securityUtil = securityUtil;
    }


    @Override
    public JsonSend createInvoice() {
        Optional<Cart> cart = cartRepository.findByUserId(securityUtil.getCurrentUser().getId());
        Date date = Date.from(Instant.now());
        if (cart.isPresent()) {
            Cart getCart = cartRepository.getOne(securityUtil.getCurrentUser().getId());
            Optional<Invoice> invoice1 = invoiceRepository.getByCartId(getCart.getId());
            if (invoice1.isPresent()) {
                if (date.before(invoice1.get().getExpDate())) {
                    InvoiceResponse invoiceResponse = new InvoiceResponse();
                    System.out.println(date + "\n" + invoice1.get().getExpDate());
                    List<CartItems> list = cart_itemsRepository.getAllProductByCartId(cart.get().getId());
                    List<String> products = new ArrayList<>();
                    int amount = 0;
                    for (CartItems cart_items : list) {
                        amount += cart_items.getQuantity() * cart_items.getProduct().getPrice();
                        products.add(cart_items.getProduct().getName());
                    }
                    invoiceResponse.setId(invoice1.get().getId());
                    invoiceResponse.setAmount(amount);
                    invoiceResponse.setStatus(true);
                    invoiceResponse.setUserName(securityUtil.getCurrentUser().getUsername());
                    invoiceResponse.setProducts(products);
                    return JsonSend.success("200", invoiceResponse);
                } else
                    return getJsonSend(cart, date, getCart);
            } else
                return getJsonSend(cart, date, getCart);
        } else
            return JsonSend.error("Cart Not found!", "500");
    }

    private JsonSend getJsonSend(Optional<Cart> cart, Date date, Cart getCart) {
        Invoice invoice = new Invoice();
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoice.setCart(getCart);
        invoice.setIssueDate(date);
        Date expDate = DateUtils.addHours(date, 2);
        invoice.setExpDate(expDate);
        invoice.setStatus(true);
        List<CartItems> list = cart_itemsRepository.getAllProductByCartId(cart.get().getId());
        List<String> products = new ArrayList<>();
        int amount = 0;
        for (CartItems cart_items : list) {
            amount += cart_items.getQuantity() * cart_items.getProduct().getPrice();
            products.add(cart_items.getProduct().getName());
        }
        invoice.setAmount((double) amount);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        if (savedInvoice != null) {
            invoiceResponse.setId(invoice.getId());
            invoiceResponse.setAmount(amount);
            invoiceResponse.setStatus(true);
            invoiceResponse.setUserName(securityUtil.getCurrentUser().getUsername());
            invoiceResponse.setProducts(products);
            return JsonSend.success("200", invoiceResponse);
        }
        return JsonSend.error("Invoice Not Created!", "500");
    }
}
