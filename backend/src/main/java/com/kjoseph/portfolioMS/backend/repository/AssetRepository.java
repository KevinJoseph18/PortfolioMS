package com.kjoseph.portfolioMS.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kjoseph.portfolioMS.backend.model.*;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
	@Query("SELECT u FROM Asset u WHERE u.id = :assetId")
	public Asset getAssetById(@Param("assetId") Long assetId);
	
	@Query(value="SELECT * FROM ASSETS WHERE Assets.ACCOUNT_ID = :accountId ORDER BY portfolio_Id", nativeQuery=true)
	public List<Asset> getMyPortfolio(@Param("accountId") Long accountId);
	
	@Query(value="SELECT * FROM ASSETS WHERE PORTFOLIO_ID = :portfolioId and ticker = :ticker", nativeQuery=true)
	public Asset getAssetByPortfolioTicker(@Param("portfolioId") Long portfolioId, @Param("ticker") String ticker);
	
}
