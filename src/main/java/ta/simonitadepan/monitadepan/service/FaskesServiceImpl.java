package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.FaskesModel;
import ta.simonitadepan.monitadepan.repository.FaskesDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FaskesServiceImpl implements  FaskesService {
    @Autowired
    FaskesDb faskesDb;

    @Override
    public List<FaskesModel> getAllFaskes(){
        return faskesDb.findAll();
    }

}
