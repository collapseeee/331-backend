package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.lab.entity.Bid;
import se331.lab.repository.BidRepository;

@Repository
@RequiredArgsConstructor
public class BidDaoImpl implements BidDao {
    final BidRepository bidRepository;
    @Override
    public Integer getBidSize() {
        return Math.toIntExact(bidRepository.count());
    }

    @Override
    public Page<Bid> getBids(Pageable pageRequest) {
        return bidRepository.findAll(pageRequest);
    }
}
