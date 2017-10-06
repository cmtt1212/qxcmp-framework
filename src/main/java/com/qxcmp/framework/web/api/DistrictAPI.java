package com.qxcmp.framework.web.api;

import com.qxcmp.framework.domain.District;
import com.qxcmp.framework.domain.DistrictService;
import com.qxcmp.framework.web.QXCMPController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 行政区API
 *
 * @author aaric
 */
@Controller
@RequestMapping("/api/district/")
@RequiredArgsConstructor
public class DistrictAPI extends QXCMPController {

    private final DistrictService districtService;

    @GetMapping("{id}")
    public ResponseEntity<List<District>> getById(@PathVariable String id) {
        return ResponseEntity.ok(districtService.getInferiors(id));
    }
}
