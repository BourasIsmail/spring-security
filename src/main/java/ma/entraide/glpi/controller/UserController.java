package ma.entraide.glpi.controller;

import ma.entraide.glpi.entity.AuthRequest;
import ma.entraide.glpi.entity.UserInfo;
import ma.entraide.glpi.service.JwtService;
import ma.entraide.glpi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring Security tutorials !!";
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserInfo userInfo) {
        String result = userInfoService.addUser(userInfo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String addUser(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(authRequest.getEmail());
        }else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        List<UserInfo> users = userInfoService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUsers/{id}")
    @PreAuthorize("hasAuthority('USER_ROLES')")
    public ResponseEntity<UserInfo> getAllUsers(@PathVariable Integer id) {
        UserInfo user = userInfoService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getUsersByName/{name}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<List<UserInfo>> getUsersByName(@PathVariable String name) {
        List<UserInfo> users = userInfoService.getUserByName(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUsersByDivision/{division}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<List<UserInfo>> getUsersByDivision(@PathVariable String division) {
        List<UserInfo> users = userInfoService.getUserByDivision(division);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id){
        userInfoService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!.");
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<UserInfo> updateUser(@PathVariable("id") Integer id, @RequestBody UserInfo user){
        return new ResponseEntity<>(userInfoService.updateUser(id, user), HttpStatus.OK);
    }

}
