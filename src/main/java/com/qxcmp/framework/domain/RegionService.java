package com.qxcmp.framework.domain;

import com.qxcmp.framework.core.QXCMPConfigurator;
import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.List;

@Service
public class RegionService extends AbstractEntityService<Region, String, RegionRepository> implements QXCMPConfigurator {

    public RegionService(RegionRepository repository) {
        super(repository);
    }

    public List<Region> findByLevel(RegionLevel level) {
        return repository.findByLevel(level);
    }

    List<Region> findInferiors(Region region) {
        return repository.findInferiors(region.getCode());
    }

    List<Region> findInferiors(String parent) {
        return repository.findInferiors(parent);
    }

    @Override
    protected <S extends Region> String getEntityId(S entity) {
        return entity.getCode();
    }

    @Override
    public void config() throws Exception {
        try {
            Resource areaFile = new ClassPathResource("/district/District.csv");

            CSVFormat.EXCEL.parse(new InputStreamReader(areaFile.getInputStream())).forEach(record -> {
                try {
                    String id = record.get(0);
                    String name = record.get(1);

                    if (!findOne("86").isPresent()) {
                        create(() -> {
                            Region region = next();
                            region.setCode("86");
                            region.setLevel(RegionLevel.STATE);
                            region.setName("中国");
                            return region;
                        });
                    }

                    if (!findOne(id).isPresent()) {
                        Region region = next();

                        region.setCode(id);
                        region.setName(name);

                        if (StringUtils.endsWith(id, "0000")) {
                            region.setLevel(RegionLevel.PROVINCE);
                            region.setParent("86");
                        } else if (StringUtils.endsWith(id, "00")) {
                            region.setLevel(RegionLevel.CITY);
                            region.setParent(StringUtils.substring(id, 0, 2) + "0000");
                        } else {
                            region.setLevel(RegionLevel.COUNTY);
                            region.setParent(StringUtils.substring(id, 0, 4) + "00");
                        }

                        create(() -> region);
                    }

                } catch (NumberFormatException ignored) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE + 4;
    }
}
