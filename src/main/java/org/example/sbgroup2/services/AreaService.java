package org.example.sbgroup2.services;

import org.example.sbgroup2.ResourceNotFoundException;
import org.example.sbgroup2.models.Area;
import org.example.sbgroup2.models.Area;
import org.example.sbgroup2.repositories.AreaRepository;
import org.example.sbgroup2.repositories.MasterDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private MasterDataRepository masterRepo;


    public Area updateArea(Long id, Area updatedArea) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Area not found"));

        area.setName(updatedArea.getName());
        area.setPurchaseAmount(updatedArea.getPurchaseAmount());
        area.setPaidAmount(updatedArea.getPaidAmount());
        area.setDueAmount(updatedArea.getDueAmount());

        return areaRepository.save(area);
    }

//    public Area getAreaSummary(long id) {
//        return areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area not found"));
//    }

    public Area recalculateArea(Long areaId) {

        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Area not found"));

        BigDecimal totalPurchase = masterRepo.sumPurchaseByArea(areaId);
        BigDecimal totalPaid = masterRepo.sumPaidByArea(areaId);

        area.setPurchaseAmount(BigDecimal.valueOf(totalPurchase.intValue()));
        area.setPaidAmount(BigDecimal.valueOf(totalPaid.intValue()));
        area.setDueAmount(BigDecimal.valueOf(totalPurchase.subtract(totalPaid).intValue()));

        return areaRepository.save(area);
    }

    public Area getAreaSummary(Long areaId) {
        return recalculateArea(areaId);
    }

    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    public Area getAreaById(Long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found"));
    }

    public Area getAreaByName(String name) {
        return areaRepository.findByName(name);
    }

    public Area createArea(Area area) {
        return areaRepository.save(area);
    }



    public void deleteArea(Long id) {
        Area area = getAreaById(id);
        areaRepository.delete(area);
    }



}
