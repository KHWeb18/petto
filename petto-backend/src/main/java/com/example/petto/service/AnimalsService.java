package com.example.petto.service;

import com.example.petto.entity.Animals;

import java.util.List;

public interface AnimalsService {

    public List<Animals> list() throws Exception;

    public List<Animals> listByLocation(String cityName);

    public Animals getAnimalsInfo(String id);

    public List<Animals> oldList() throws Exception;

    public List<Animals> myLikedAnimals(long memberNo) throws Exception;

    public Long getNumOfAnimals();

    public List<Animals> filterAnimals(String[] selectedPlace, String[] selectedKinds);

    public void plusSharedCnt(String notice_no);
}
