package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.dto.EntityToDTO;
import sg.edu.nus.ad_backend.dto.MemberDTO;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.service.IMemberService;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final IMemberService memberService;

    public MemberController(IMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers().stream().map(EntityToDTO::memberToDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable("id") Long id) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EntityToDTO.memberToDto(member));
    }

    /**
     * Create a member.
     * This api should be called at user registration
     *
     * @param member member info
     * @return Created member
     */
    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@RequestBody Member member) {
        return ResponseEntity.ok(EntityToDTO.memberToDto(memberService.saveMember(member)));
    }

    /**
     * Update a member's info.
     * Note that we do not handle password changes here.
     * Member id and password inside parameter 'member' will be ignored.
     *
     * @param id     member ID
     * @param member updated info
     * @return updated member dto
     */
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable("id") Long id, @RequestBody Member member) {
        Member existingMember = memberService.getMemberById(id);
        if (existingMember == null) {
            return ResponseEntity.notFound().build();
        }
        member.setId(id);
        member.setPassword(existingMember.getPassword());
        return ResponseEntity.ok(EntityToDTO.memberToDto(memberService.saveMember(member)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable("id") Long id) {
        Member existingMember = memberService.getMemberById(id);
        if (existingMember == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(memberService.deleteMemberById(id));
    }
}
