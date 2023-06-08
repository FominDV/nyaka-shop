package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.dto.CountryDto;
import ru.fomin.nyakashop.dto.FeedbackDto;
import ru.fomin.nyakashop.dto.FeedbackPageDto;
import ru.fomin.nyakashop.entities.Feedback;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.enums.OrderStatus;
import ru.fomin.nyakashop.repositories.FeedbackRepository;
import ru.fomin.nyakashop.repositories.OrderItemRepository;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {

    final FeedbackRepository feedbackRepository;
    final OrderItemRepository orderItemRepository;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
    @Value("${pageSize.feedback}")
    int pageSize;

    @GetMapping
    public FeedbackPageDto getFeedbacks(@RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex) {

        List<FeedbackDto> feedbackDtos = productId==null?Collections.emptyList(): feedbackRepository.findAllByOrderItem_Product_Id(productId).stream()
                .sorted(Comparator.comparing(Feedback::getCreatedAt))
                .map(f->FeedbackDto.builder()
                        .text(f.getText())
                        .date(dtf.format(f.getCreatedAt()))
                        .author(f.getOrderItem().getOrder().getUser().getLogin())
                        .build())
                .collect(Collectors.toList());
        Collections.reverse(feedbackDtos);
        pageIndex--;
        Integer pagesLength = feedbackDtos.size()/pageSize + (feedbackDtos.size()%pageSize==0?0:1);
        int startIndex = pageSize*pageIndex;
        int endIndex = pageIndex==pagesLength-1?startIndex -1+  feedbackDtos.size() - (pagesLength-1)*pageSize:startIndex + pageSize -1;
        feedbackDtos= feedbackDtos.isEmpty()?feedbackDtos: feedbackDtos.subList(startIndex,endIndex+1);
        return FeedbackPageDto.builder()
                .feedbacks(feedbackDtos)
                .totalPages(pagesLength)
                .build();
    }

    @GetMapping("/create")
    @Transactional
    public void createFeedback(@RequestParam(name = "text") String text, @RequestParam(name = "productId") Long productId) {
        OrderItem orderItem = orderItemRepository.findAllByProduct_IdAndOrder_User_LoginAndOrder_Status(productId, SecurityUtils.getEmail(), OrderStatus.FINISHED).get(0);
       Feedback feedback = Feedback.builder()
               .text(text)
               .orderItem(orderItem)
               .build();
       feedbackRepository.save(feedback);
    }

}
