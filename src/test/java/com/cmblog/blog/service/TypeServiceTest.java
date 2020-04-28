package com.cmblog.blog.service;

import com.cmblog.blog.po.Type;
import com.cmblog.blog.service.impl.TypeServiceImpl;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public class TypeServiceTest {

    @Test
    public void TypeFindAll(){
        ITypeService typeService = new TypeServiceImpl();
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 2;
            }

            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        List<Type> types = typeService.listType();
        System.out.println(types);
//        System.out.println(types);
    }
}
