package com.example.demo.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.common.pojo.User;
import com.example.demo.common.pojo.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BeanCopierUtils {


    /**
     * 对象转换
     * TODO 性能大幅优于orika
     * @param source
     * @param destinationClass
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> D map(S source, Class<D> destinationClass){
        BeanCopier copier = BeanCopier.create(source.getClass(),destinationClass,false);
        D destination = null;
        try {
            destination = destinationClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        copier.copy(source,destination,null);
        return destination;
    }


    /**
     * 集合转换
     * TODO 由于BeanCopier没有提供批量转换的功能，所以集合的转换其实是循环调用对象转换，性能略低于orika提供的原生的集合转换的api
     * @param sourceList
     * @param destinationClass
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> List<D> mapList(List<S> sourceList, Class<D> destinationClass){
        List<D> destinationList = new ArrayList<>();
        for (S source : sourceList) {
            destinationList.add(map(source, destinationClass));
        }
        return destinationList;
    }

    public static void main(String[] args) {

        log.info("----------------------------------------------------------------对象互转----------------------------------------------------------------");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("张三");
        userDTO.setUserSex("1");
        userDTO.setTest("test");
        //TODO 子类的toString方法无法打印输出从父类继承的属性的值(但实际上是存在的，可以通过get方法获取)，可以通过转换为JSON查看。
        log.info("源userDTO：" + JSONObject.toJSONString(userDTO, SerializerFeature.WriteMapNullValue));
        //dto->entity
        User user = BeanCopierUtils.map(userDTO, User.class);
        log.info("目标user：" + JSONObject.toJSONString(user, SerializerFeature.WriteMapNullValue));
        //entity->dto
        log.info("目标userDTO：" + JSONObject.toJSONString(BeanCopierUtils.map(user, UserDTO.class), SerializerFeature.WriteMapNullValue));

        log.info("----------------------------------------------------------------集合互转----------------------------------------------------------------");
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setUserName("张三");
        userDTO1.setUserSex("1");
        userDTO1.setTest("test");
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setUserName("李四");
        userDTO2.setUserSex("2");
        userDTO2.setTest("test");
        userDTOList.add(userDTO1);
        userDTOList.add(userDTO2);
        log.info("源userDTOList：" + JSONObject.toJSONString(userDTOList, SerializerFeature.WriteMapNullValue));
        //List<dto>->List<entity>
        List<User> userList = BeanCopierUtils.mapList(new ArrayList<>(), User.class);
        log.info("目标userList：" + JSONObject.toJSONString(userList, SerializerFeature.WriteMapNullValue));
        log.info("目标userDTOList：" + JSONObject.toJSONString(BeanCopierUtils.mapList(userList, UserDTO.class), SerializerFeature.WriteMapNullValue));

        log.info("----------------------------------------------------------------性能测试----------------------------------------------------------------");

        long begin1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            BeanCopierUtils.map(userDTO,User.class);
        }
        long end1 = System.currentTimeMillis();
        log.info("对象转换时间：" + (end1 - begin1));

        List<UserDTO> userDTOListPerformanceTest = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            userDTOListPerformanceTest.add(userDTO);
        }
        long begin2 = System.currentTimeMillis();
        BeanCopierUtils.mapList(userDTOListPerformanceTest,User.class);
        long end2 = System.currentTimeMillis();
        log.info("集合转换时间：" + (end2 - begin2));
    }
}