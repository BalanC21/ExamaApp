package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.dto.RevenueDTO;
import com.codecool.jpasecurity.exceptions.RevenueNotFoundException;
import com.codecool.jpasecurity.model.Revenue;
import com.codecool.jpasecurity.model.User;
import com.codecool.jpasecurity.repository.RevenueRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RevenueService {
    private final RevenueRepository repository;
    private final GetUsers getUsers;

    public RevenueService(RevenueRepository repository, GetUsers getUsers) {
        this.repository = repository;
        this.getUsers = getUsers;
    }

    public RevenueDTO addRevenue(RevenueDTO revenueDTO) {
        if (findRevenueByUser().getAmount() == null) {
            repository.save(toRevenue(revenueDTO));
        } else {
            System.out.println("Revenue for user: " + getUsers.getUser().getUsername() + " already exist!");
        }
        return revenueDTO;
    }

    public Revenue findRevenueByUser() {
        User owner = getUsers.getUser();
        return repository.findByOwner_Username(owner.getUsername())
                .orElseThrow(RevenueNotFoundException::new);
    }

    public Revenue modifyRevenue(Revenue revenueAmount) {
        Revenue revenue = findRevenueByUser();
        long newRevenueAmount = revenue.getAmount() - revenueAmount.getAmount();
        revenue.setAmount(newRevenueAmount);
        repository.save(revenue);
        return revenue;
    }

    private Revenue toRevenue(RevenueDTO revenueDTO) {
        User owner = getUsers.getUser();
        Revenue newRevenue = new Revenue();
        newRevenue.setOwner(owner);
        newRevenue.setAmount(revenueDTO.amount());
        newRevenue.setRecursive(revenueDTO.isRecursive());
        newRevenue.setRenewalDayOfMonth(revenueDTO.renewalDayOfMonth());
        return newRevenue;
    }
}