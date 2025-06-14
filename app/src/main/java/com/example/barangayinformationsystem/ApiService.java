package com.example.barangayinformationsystem;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {

    // --- Existing Non-Chat Methods (Unchanged) ---
    @FormUrlEncoded
    @POST("android/login")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("android/check-verification")
    Call<VerificationResponse> checkVerificationStatus(@Query("user_id") int userId);

    @GET("android/fetch-user-details")
    Call<UserDetailsResponse> getUserDetails(@Query("id") int userId);

    @Multipart
    @POST("android/register-user")
    Call<RegistrationResponse> registerUser(
            @Part("firstName") RequestBody firstName,
            @Part("lastName") RequestBody lastName,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("age") RequestBody age,
            @Part("birthday") RequestBody birthday,
            @Part("adrHouseNo") RequestBody houseNumber,
            @Part("adrZone") RequestBody zone,
            @Part("adrStreet") RequestBody street,
            @Part("gender") RequestBody gender,
            @Part MultipartBody.Part validId,
            @Part MultipartBody.Part validIdBack
    );

    @GET("android/get_announcements")
    Call<List<AnnouncementResponse>> getAnnouncements();

    @FormUrlEncoded
    @POST("android/incident-reports")
    Call<IncidentReportResponse> submitIncidentReport(
            @Field("user_id") int userId,
            @Field("title") String title,
            @Field("description") String description,
            @Field("media_data") String mediaData
    );

    @Multipart
    @POST("android/incident-reports-video")
    Call<VideoUploadResponse> uploadVideo(
            @Part("api_key") RequestBody apiKey,
            @Part("timestamp") RequestBody timestamp,
            @Part("signature") RequestBody signature,
            @Part("folder") RequestBody folder,
            @Part("public_id") RequestBody publicId,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("android/update_user_activity")
    Call<ActivityResponse> updateUserActivity(
            @Field("user_id") int userId
    );

    @Multipart
    @POST("android/update-user-profile")
    Call<UpdateProfileResponse> updateUserProfile(
            @Part("user_id") RequestBody userId,
            @Part("username") RequestBody username,
            @Part("adrHouseNo") RequestBody houseNo,
            @Part("adrStreet") RequestBody street,
            @Part("adrZone") RequestBody zone,
            @Part("password") RequestBody password,
            @Part("currentPassword") RequestBody currentPassword,
            @Part MultipartBody.Part profilePicture
    );

    @FormUrlEncoded
    @POST("android/document-requests")
    Call<DocumentRequestResponse> submitDocumentRequest(
            @Field("userId") int userId,
            @Field("documentType") String documentType,
            @Field("name") String name,
            @Field("address") String address,
            @Field("tin") String tin,
            @Field("ctc") String ctc,
            @Field("alias") String alias,
            @Field("age") int age,
            @Field("birthday") String birthday,
            @Field("placeOfBirth") String placeOfBirth,
            @Field("occupation") String occupation,
            @Field("lengthOfStay") int lengthOfStay,
            @Field("citizenship") String citizenship,
            @Field("gender") String gender,
            @Field("civilStatus") String civilStatus,
            @Field("purpose") String purpose,
            @Field("quantity") int quantity
    );

    @Multipart
    @POST("android/upload-requirements")
    Call<UploadRequirementsResponse> uploadRequirements(
            @Part("requestId") RequestBody requestId,
            @Part("quantity") RequestBody quantity,
            @Part MultipartBody.Part frontId,
            @Part MultipartBody.Part backId
    );

    @GET("android/get-user-requests")
    Call<DocumentRequestListResponse> getUserRequests(@Query("userId") int userId);

    @POST("android/cancel-request")
    @FormUrlEncoded
    Call<DocumentRequestResponse> cancelRequest(
            @Field("requestId") int requestId,
            @Field("reason") String reason
    );

    @GET("android/user-incident-reports")
    Call<IncidentReportListResponse> getUserIncidentReports(@Query("userId") int userId);


    // --- UPDATED Chat Methods ---

    /**
     * Sends a chat message from the user.
     * Calls the new Laravel API endpoint POST /api/chat/messages
     */
    @FormUrlEncoded
    @POST("chat/messages") // <-- UPDATED PATH
    Call<MessageResponse> sendMessage(
            @Field("message") String message,
            // Added sender_id back to match DeskChatFragment call.
            // IMPORTANT: Backend should ideally ignore this and use authenticated user ID from token/session.
            @Field("sender_id") int senderId
    );


    /**
     * Gets the message history for a user's chat.
     * Calls the new Laravel API endpoint GET /api/chat/messages
     */
    @GET("chat/messages") // <-- UPDATED PATH
    Call<List<ChatMessage>> getMessages(
            // Keep user_id query param for now to match temporary backend logic.
            // Ideally, backend should get this from authenticated user.
            @Query("user_id") int userId
    );

    /**
     * Checks for new messages since the last known timestamp.
     * Calls the new Laravel API endpoint GET /api/chat/messages/new
     */
    @GET("chat/messages/new") // <-- UPDATED PATH
    Call<MessageCheckResponse> checkNewMessages(
            // Keep user_id query param for now.
            @Query("user_id") int userId,
            @Query("last_message_timestamp") long lastMessageTimestamp // Keep timestamp param (milliseconds)
    );

    /**
     * Get user notifications
     */
    @GET("android/notifications")
    Call<NotificationListResponse> getUserNotifications(@Query("user_id") int userId);

    /**
     * Mark notification as read
     */
    @FormUrlEncoded
    @POST("android/notifications/mark-read")
    Call<ApiResponse> markNotificationAsRead(@Field("notification_id") int notificationId);

}
