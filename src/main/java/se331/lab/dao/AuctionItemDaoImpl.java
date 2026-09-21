package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.lab.entity.AuctionItem;
import se331.lab.repository.AuctionItemRepository;

@Repository
@RequiredArgsConstructor
public class AuctionItemDaoImpl implements AuctionItemDao {
    final AuctionItemRepository auctionItemRepository;
    @Override
    public Integer getAuctionItemSize() {
        return Math.toIntExact(auctionItemRepository.count());
    }

    @Override
    public Page<AuctionItem> getAuctionItems(Pageable pageRequest) {
        return auctionItemRepository.findAll(pageRequest);
    }

    @Override
    public Page<AuctionItem> getAuctionItems(String description, Pageable pageRequest) {
        return auctionItemRepository.findByDescriptionIgnoreCaseContainingOrTypeIgnoreCaseContaining(description, description, pageRequest);
    }

    @Override
    public Page<AuctionItem> getAuctionItems(Integer amount, Pageable pageRequest) {
        return auctionItemRepository.findBySuccessfulBid_AmountLessThan(Double.parseDouble(amount.toString()), pageRequest);
    }
}
