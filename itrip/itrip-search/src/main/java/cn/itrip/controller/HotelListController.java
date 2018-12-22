package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.vo.hotel.HotelVideoDescVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.service.hotel.ItripHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;



@Controller
@Api(value = "API", basePath = "/http://api.itrap.com/api")
@RequestMapping(value = "/api/hotellist")
public class HotelListController {

    private Logger logger = Logger.getLogger(HotelListController.class);

    @Resource
    private ItripHotelService itripHotelService;


    @ApiOperation(value = "查询热门城市", httpMethod = "GET",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "查询国内、国外的热门城市(1:国内 2:国外)" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>" +
            "<p>10201 : hotelId不能为空 </p>" +
            "<p>10202 : 系统异常,获取失败</p>")
    @RequestMapping(value = "/searchItripHotelListByHotCity", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripHotel> searchItripHotelListByHotCity(@PathVariable Integer cityId) {
        List<ItripHotel> itripHotels = null;
        List<HotelVideoDescVO> hotelVideoDescVOS = null;
        try {
            if (EmptyUtils.isNotEmpty(cityId)) {

                itripHotels = itripHotelService.getItripHotelId(cityId);
                if (EmptyUtils.isNotEmpty(itripHotels)) {
                    hotelVideoDescVOS = new ArrayList();
                    for (ItripHotel itripHotel : itripHotels) {
                        HotelVideoDescVO vo = new HotelVideoDescVO();
                        BeanUtils.copyProperties(itripHotel, vo);
                        hotelVideoDescVOS.add(vo);
                    }
                }

            } else {
                DtoUtil.returnFail("type不能为空", "10201");
            }
        } catch (Exception e) {
            DtoUtil.returnFail("系统异常", "10202");
            e.printStackTrace();
        }
        return DtoUtil.returnDataSuccess(hotelVideoDescVOS);
    }

}
