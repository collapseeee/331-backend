package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se331.lab.entity.AuctionItem;
import se331.lab.service.AuctionItemService;
import se331.lab.util.LabMapper;

@RestController
@RequiredArgsConstructor
public class AuctionItemController {
    final AuctionItemService auctionItemService;

    @GetMapping("/auctionItems")
    public ResponseEntity<?> getAuctionItems(@RequestParam(value = "_limit", required = false) Integer perPage,
                                             @RequestParam(value = "_page", required = false) Integer page
    ) {
        perPage = perPage == null ? auctionItemService.getAuctionItemSize() : perPage;
        page = page == null ? 1 : page;
        Page<AuctionItem> pageOutput;
        pageOutput =  auctionItemService.getAuctionItems(PageRequest.of(page - 1, perPage));
        return ResponseEntity.ok(LabMapper.INSTANCE.getAuctionItemDto(pageOutput.getContent()));
    }
}
