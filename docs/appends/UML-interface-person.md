| #   | Action Name                   | Applicable To   | Interface   | Suggested Fields (if needed)          | Suggested Methods                                     |
| --- | ----------------------------- | --------------- | ----------- | ------------------------------------- | ----------------------------------------------------- |
| 1   | Register account              | Owner           | Registrable | String username, String email         | void register(String username, String email)          |
| 2   | Send notification             | Owner, Reviewer | Notifiable  | String message, NotificationType type | void notify(String message)                           |
| 3   | Follow a user                 | Reviewer        | Followable  | long targetUserId                     | void follow(long targetUserId)                        |
| 4   | Unfollow a user               | Reviewer        | Followable  | long targetUserId                     | void unfollow(long targetUserId)                      |
| 5   | Receive email alert           | Owner, Reviewer | Notifiable  | -                                     | void sendEmailAlert(String subject, String body)      |
| 6   | Verify registration           | Owner           | Registrable | String verificationCode               | boolean verifyRegistration(String code)               |
| 7   | Get list of followers         | Reviewer        | Followable  | -                                     | List<Long> getFollowers()                             |
| 8   | Get list of following         | Reviewer        | Followable  | -                                     | List<Long> getFollowing()                             |
| 9   | Push real-time notification   | Owner, Reviewer | Notifiable  | String channel                        | void pushNotification(String message, String channel) |
| 10  | Complete profile registration | Owner           | Registrable | Map<String, String> profileData       | void completeRegistration(Map<String, String> data)   |

| #   | Action Name                  | Applicable To   | Interface         | Suggested Fields (if needed)                 | Suggested Methods                                                          |
| --- | ---------------------------- | --------------- | ----------------- | -------------------------------------------- | -------------------------------------------------------------------------- |
| 1   | Manage property listings     | Owner           | Listable          | long propertyId, String description          | void addListing(long propertyId); List<Long> getListings()                 |
| 2   | Upload property photos       | Owner           | Uploadable        | List<String> photoUrls                       | void uploadPhotos(List<String> urls); boolean removePhoto(String url)      |
| 3   | Submit property review       | Reviewer        | Reviewable        | int rating, String comment                   | void submitReview(int rating, String comment)                              |
| 4   | Rate a property              | Reviewer        | Rateable          | int stars (1-5)                              | void rateProperty(int stars); double getAverageRating()                    |
| 5   | Schedule property viewing    | Owner, Reviewer | Schedulable       | DateTime viewingTime, long propertyId        | boolean scheduleViewing(DateTime time); List<DateTime> getAvailableSlots() |
| 6   | Report maintenance issue     | Reviewer        | Reportable        | String issueDescription, List<String> photos | void reportIssue(String desc); void attachPhotos(List<String> photos)      |
| 7   | Bookmark favorite properties | Owner, Reviewer | Bookmarkable      | long propertyId                              | void addBookmark(long id); Set<Long> getBookmarks()                        |
| 8   | View payment history         | Owner, Reviewer | Payable           | -                                            | List<Transaction> getPaymentHistory(); double getBalance()                 |
| 9   | Access analytics dashboard   | Owner           | AnalyticsViewable | DateRange range                              | Map<String, Object> getAnalytics(DateRange range)                          |
| 10  | Verify identity              | Owner, Reviewer | Verifiable        | String documentType, byte[] documentData     | boolean verifyIdentity(String type, byte[] data); boolean isVerified()     |
