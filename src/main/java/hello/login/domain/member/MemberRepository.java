package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;

@Slf4j
@Repository
//원래는 인터페이스로 구현해서 관리하는게 유지보수의 정석
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>(); //static사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}",member);
        store.put(member.getId(), member);
        return member;//***
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
       /* List<Member> all = findAll();
        for (Member m : all) {
            if (m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();*/

        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
        //아이디 찾기 람다 스트림 predicate인터페이스  쉽게 데이터베이스 where이라고 생각
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }


    public void clearStore() {
        store.clear();
    }



}
