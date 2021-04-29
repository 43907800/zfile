package im.zhaojun.zfile.service;

import cn.hutool.core.util.StrUtil;
import im.zhaojun.zfile.model.entity.ErgodicDirConfig;
import im.zhaojun.zfile.repository.ErgodicDirRepository;
import im.zhaojun.zfile.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author by LiuSy
 * @Date 2021/4/28 10:16
 * @Classname ErgodicDirService
 * @Description
 */

@Service
public class ErgodicDirService {

    @Resource
    ErgodicDirRepository ergodicDirRepository;


    public List<ErgodicDirConfig> findByStatus(Integer status){
        return ergodicDirRepository.findByStatus(status);
    }

    public  boolean save(ErgodicDirConfig ergodicDirConfig){
        ergodicDirConfig.setFilePath(StringUtils.removeDuplicateSeparator(ergodicDirConfig.getFilePath()));
        ErgodicDirConfig erg = ergodicDirRepository.findByDriveIdAndFilePath(ergodicDirConfig.getDriveId(), ergodicDirConfig.getFilePath());
        if (erg != null){
            erg.setCategory(ergodicDirConfig.getCategory());
            erg.setTypechoVideoStr(ergodicDirConfig.getTypechoVideoStr());
            erg.setTitle(ergodicDirConfig.getTitle());
            erg.setStatus(ergodicDirConfig.getStatus());
            ergodicDirConfig = erg;
        }
        ErgodicDirConfig save = ergodicDirRepository.save(ergodicDirConfig);
        return save == null ? false : true;
    }

    public  void delete(Integer id){
         ergodicDirRepository.deleteById(id);
    }

    public ErgodicDirConfig findById(Integer id){
        Optional<ErgodicDirConfig> optional = ergodicDirRepository.findById(id);
        return optional.orElse(null);
    }

    public Page<ErgodicDirConfig> list(ErgodicDirConfig ergodicDirConfig, Integer page, Integer limit, String orderBy, String orderDirection) {
        Sort sort = Sort.by("desc".equals(orderDirection) ? Sort.Direction.DESC : Sort.Direction.ASC, orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Specification<ErgodicDirConfig> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (StrUtil.isNotEmpty(ergodicDirConfig.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + ergodicDirConfig.getTitle() + '%'));
            }

            if (StrUtil.isNotEmpty(ergodicDirConfig.getFilePath())) {
                predicates.add(criteriaBuilder.like(root.get("filePath"), "%" + ergodicDirConfig.getFilePath() + '%'));
            }
            if (StrUtil.isNotEmpty(ergodicDirConfig.getCategory())) {
                predicates.add(criteriaBuilder.like(root.get("category"), "%" + ergodicDirConfig.getCategory() + '%'));
            }

            if (ergodicDirConfig.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), ergodicDirConfig.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return ergodicDirRepository.findAll(specification, pageable);
    }

    public List<String> findCategory(){
        return ergodicDirRepository.findCategory();
    }
}
