package com.kjoseph.portfolioMS.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kjoseph.portfolioMS.backend.model.Asset;
import com.kjoseph.portfolioMS.backend.model.User;
import com.kjoseph.portfolioMS.backend.repository.AssetRepository;
import com.kjoseph.portfolioMS.backend.repository.UserRepository;

@RestController
public class MainController {

	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public long currentUser () {
		User u = userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return u.getId();
	}
	
	@GetMapping("/assets")
	public List<Asset> getAllAssets() {
		return (List<Asset>) assetRepository.findAll();
	}
	
	@GetMapping("/assets/{id}")
	public ResponseEntity<Asset> getAssetById(@PathVariable(value= "id") Long assetId) {
		Asset asset = assetRepository.getAssetById(assetId);
		return ResponseEntity.ok().body(asset);
	}
	
	@PostMapping("/assets")
	public ResponseEntity<@Valid Asset> createAsset(@Valid @RequestBody Asset asset) {
		return ResponseEntity.ok().body(assetRepository.save(asset));
	}
	
	@PutMapping("/assets/{id}")
	public ResponseEntity<Asset> updateAsset(@PathVariable(value="id") Long id, @Valid @RequestBody Asset asset) {
		Asset updatedAsset = assetRepository.getAssetById(id);
		if (asset.getAmount() > 0) updatedAsset.setAmount(asset.getAmount());
		if (!(asset.getAssetType()==null)) updatedAsset.setAssetType(asset.getAssetType());
		if (asset.getAvgPurchasePrice() != 0) updatedAsset.setAvgPurchasePrice(asset.getAvgPurchasePrice());
		if (asset.getPortfolioId() != 0) updatedAsset.setPortfolioId(asset.getPortfolioId());
		if (!(asset.getTicker() == null)) updatedAsset.setTicker(asset.getTicker());
		return ResponseEntity.ok().body(assetRepository.save(updatedAsset));
	}
	
	@DeleteMapping("/assets/{id}")
	public Map<String, Boolean> deleteAsset(@PathVariable(value="id") Long id) {
		Asset asset = assetRepository.getAssetById(id);
		assetRepository.delete(asset);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", true);
		return response;
	}
	
	public ResponseEntity<Asset> updateExistingAssetCount(Asset oldAsset, Asset asset) {
		// check preconditions
		Asset updatedAsset = oldAsset;
		updatedAsset.setAvgPurchasePrice(updatedAsset.getAvgPurchasePrice() * updatedAsset.getAmount() + asset.getAvgPurchasePrice() * asset.getAmount());
		updatedAsset.setAmount(updatedAsset.getAmount() + asset.getAmount());
		updatedAsset.setAvgPurchasePrice(updatedAsset.getAvgPurchasePrice()/updatedAsset.getAmount());
		updatedAsset = assetRepository.save(updatedAsset);
		return ResponseEntity.ok().body(updatedAsset);
	}
	
	@GetMapping("/myPortfolio")
	public List<Asset> getMyPortfolio() {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userRepository.getUserByUsername(currentUser);
		return assetRepository.getMyPortfolio(u.getId());
	}
	
	@PostMapping("/buyAsset")
	public ResponseEntity<Asset> buyAsset(@Valid @RequestBody Asset asset){
		Asset updatedAsset = assetRepository.getAssetByPortfolioTicker(asset.getPortfolioId(), asset.getTicker());
		if (updatedAsset == null) {
			updatedAsset = asset;
		}
		else {
			updatedAsset.setAvgPurchasePrice(updatedAsset.getAvgPurchasePrice() * updatedAsset.getAmount() + asset.getAvgPurchasePrice() * asset.getAmount());
			updatedAsset.setAmount(updatedAsset.getAmount() + asset.getAmount());
			updatedAsset.setAvgPurchasePrice(updatedAsset.getAvgPurchasePrice()/updatedAsset.getAmount());
		}
		return ResponseEntity.ok().body(assetRepository.save(updatedAsset));
	}
}
