package com.akhil.trading.service.impl;

import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.Asset;
import com.akhil.trading.model.Coin;
import com.akhil.trading.repo.AssetRepo;
import com.akhil.trading.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetRepo assetRepo;
    @Override
    public void createAsset(Long userId, Coin coin, BigDecimal quantity) {
        Asset asset=new Asset();
        asset.setUserId(userId);
        asset.setCoinId(coin.getId());
        asset.setQuantity(quantity);
        asset.setBuyPrice(BigDecimal.valueOf(coin.getCurrentPrice()));
        assetRepo.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) {
        return assetRepo.findById(assetId).orElseThrow(()->new ResourceNotFoundException("asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return assetRepo.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, BigDecimal quantity) {
        Asset oldAsset=getAssetById(assetId);
        oldAsset.setQuantity(oldAsset.getQuantity().add(quantity));
        return assetRepo.save(oldAsset);
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepo.findByUserIdAndCoinId(userId,coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepo.deleteById(assetId);

    }
}
