package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.AddFriendRequest;
import com.example.Comp1640.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddFriendRequestRepository extends JpaRepository<AddFriendRequest,Long> {

    @Query(value = "SELECT * FROM addfriendrequest " +
            "WHERE (recipient_id = :recipientId) ", nativeQuery = true)
    List<AddFriendRequest> findMyRequestIReceived( @Param("recipientId") Long recipientId);



}
