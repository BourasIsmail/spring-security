package ma.entraide.glpi.service;

import ma.entraide.glpi.entity.Materiel;
import ma.entraide.glpi.entity.UserInfo;
import ma.entraide.glpi.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
    }
    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }
    public List<UserInfo> getAllUser(){
         return userInfoRepository.findAll();
    }
    public UserInfo getUser(Integer id){
        return userInfoRepository.findById(id).get();
    }

    public List<UserInfo> getUserByName(String name){ return userInfoRepository.getUserByName(name);}

    public List<UserInfo> getUserByDivision(String division){
        return userInfoRepository.getUserByDivision((division));
    }

    public UserInfo getUserById(Integer id) {
        Optional<UserInfo> userOp = userInfoRepository.findById(id);
        UserInfo user = null;
        if(userOp.isPresent()){
            user = userOp.get();
        }
        else{
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }
    public void deleteUser(Integer id){
        userInfoRepository.deleteById(id);
    }

    public UserInfo updateUser(Integer id,UserInfo newUser){
        UserInfo user = userInfoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setDivDuService(newUser.getDivDuService());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        return userInfoRepository.save(user);
    }

    public Page<UserInfo> findUsersWithPaginationAndSortingByNom(int offset, int pageSize, String name){
        Page<UserInfo> userInfos = userInfoRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(name)));
        return  userInfos;
    }

    public Page<UserInfo> findUsersWithPaginationAndSortingByDivision(int offset, int pageSize, String divDuService){
        Page<UserInfo> userInfos = userInfoRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(divDuService)));
        return  userInfos;
    }

}
