package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.repository.BalitaDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BalitaServiceImpl implements BalitaService {
    @Autowired
    BalitaDb balitaDb;

    @Override
    public List<BalitaModel> getAllBalita() {
        return balitaDb.findAll();
    }

    @Override
    public void addBalita(BalitaModel balita, UserModel user) {
        balita.setId_pengguna(user);
        System.out.println("balita nama "+ balita.getName());
        System.out.println("ortu nama "+ balita.getId_pengguna().getName());
        balitaDb.save(balita);
    }

    @Override
    public BalitaModel getBalita(Long id) {
        return balitaDb.getById(id);
    }

    @Override
    public void deleteBalita(BalitaModel balita) {
        balitaDb.delete(balita);
    }

    @Override
    public void updateBalita(BalitaModel balita) {
        BalitaModel balitaTarget = this.getBalita(balita.getId_balita());
        balitaTarget.setName(balita.getName());
        balitaTarget.setBirth_date(balita.getBirth_date());
        balitaTarget.setGender(balita.getGender());
        balitaDb.save(balitaTarget);
        System.out.println("update balita nama "+ balita.getName());
    }
}
