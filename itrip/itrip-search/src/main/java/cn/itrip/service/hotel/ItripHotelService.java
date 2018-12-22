package cn.itrip.service.hotel;

import cn.itrip.beans.pojo.ItripHotel;

import java.util.List;

public interface ItripHotelService {
    public List<ItripHotel> getItripHotelId(Integer cityId) throws Exception;
}
