package com.qxcmp.framework.domain;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.qxcmp.framework.core.QXCMPConfigurator;
import org.apache.commons.csv.CSVFormat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService, QXCMPConfigurator {

    private Multimap<String, District> districtMultimap = ArrayListMultimap.create();

    @Override
    public List<District> getAllProvince() {
        return Lists.newArrayList(districtMultimap.get("86"));
    }

    @Override
    public List<District> getInferiors(String id) {
        return Lists.newArrayList(districtMultimap.get(id));
    }

    @Override
    public void config() throws Exception {
        try {
            Resource areaFile = new ClassPathResource("/district/District.csv");

            districtMultimap.put("86", District.builder().id("86").level(0).name("中国").build());

            CSVFormat.EXCEL.parse(new InputStreamReader(areaFile.getInputStream())).forEach(record -> {
                try {
                    String id = record.get(0);
                    String name = record.get(1);
                    process(id, name);
                } catch (NumberFormatException ignored) {

                }
            });

            /*
             * 构造完以后储存为不可变集合
             */
            districtMultimap = Multimaps.unmodifiableMultimap(districtMultimap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(String id, String name) {
        try {
            int level = parseLevel(id);

            String parent = "";

            if (level == 1) {
                parent = "86";
            } else if (level == 2) {
                parent = id.substring(0, 2) + "0000";
            } else if (level == 3) {
                parent = id.substring(0, 4) + "00";
            }

            districtMultimap.put(parent, District.builder().id(id).level(level).name(name).build());
        } catch (Exception ignored) {
        }
    }

    /**
     * 将行政区编码转换成行政区级别
     *
     * @param id 行政区编码
     *
     * @return 行政区级别
     */
    private int parseLevel(String id) {
        if (id.endsWith("0000")) {
            return 1;
        }

        if (id.endsWith("00")) {
            return 2;
        }

        return 3;
    }
}
