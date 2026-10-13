package ma.clicktrack.repository.implementation;

import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.PublisherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {

    private final List<Publisher> publishers = new ArrayList<>();

    @Override
    public Optional<Publisher> findById(long id){
        return publishers.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public List<Publisher> findAll(){
        return new ArrayList<>(publishers);
    }

    @Override
    public void save(Publisher publisher){
        Optional<Publisher> existing = findById(publisher.getId());
        if(existing.isEmpty()){
            publishers.add(publisher);
        }
    }

    @Override
    public void update(Publisher publisher){
        Optional<Publisher> existing = findById(publisher.getId());
        if(existing.isPresent()){
            int index = publishers.indexOf(existing.get());
            publishers.set(index, publisher);
        }
    }

    @Override
    public void delete(long id){
        publishers.removeIf(p -> p.getId() == id);
    }


}
