package com.ibm3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm3.dao.CorsoDao;
import com.ibm3.dto.CorsoDto;
import com.ibm3.model.Corso;

@Service
public class CorsoServiceImpl implements CorsoService{
	
	@Autowired
	private CorsoDao corsoDao; 

	@Override
	public List<CorsoDto> getCourses() {
        List<Corso> corsi = (List<Corso>) corsoDao.findAll();
        List<CorsoDto> corsiDto = new ArrayList<>();

        for (Corso corso : corsi) {
            CorsoDto corsoDto = new CorsoDto();
            
            corsoDto.setNomeCorso(corso.getNomeCorso());
            
            corsiDto.add(corsoDto);
        }

        return corsiDto;
	}

}
