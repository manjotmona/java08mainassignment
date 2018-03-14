package edu.knoldus;

/**
 * Created by pallavi on 12/3/18.
 */

import java.util.List;
import java.util.concurrent.CompletableFuture;

import twitter4j.Status;

public class Application {

    public static void main(String[] args) {
        //  try {
        TwitterOperations twitterOperations = new TwitterOperations();
        System.out.println("\n\n\n\n\n\n\n\n\n");
        CompletableFuture<List<Status>> statusListInFuture = twitterOperations.getLatestPostFromTimeLine();
        CompletableFuture<Void> statusResult = statusListInFuture.thenAccept(statusList -> statusList.forEach(status -> System.out
                .println("@" + status.getText() + " " + status.getCreatedAt() + "\n")));

        System.out.println("\n\n\n\n\n\n\n\n\n");
        CompletableFuture<List<Status>> statusListInFutureOldest = twitterOperations.getOldestPostFromTimeline();
        CompletableFuture<Void> statusResultOldest = statusListInFutureOldest.thenAccept(statusList -> statusList.forEach(status -> System.out
                .println("@" + status.getText() + " " + status.getCreatedAt() + "\n")));

        System.out.println("\n\n\n\n\n\n\n\n\n");
        CompletableFuture<Long> longCompletableFuture = twitterOperations.gettweetsOnParticularDate();
        CompletableFuture<Void> dateResult = longCompletableFuture
                .thenAccept(value -> System.out.println("\n\n\n\n\n perDay" + value));

        System.out.println("\n\n\n\n\n\n\n\n\n");
        CompletableFuture<List<Status>> averageRetweetCont = twitterOperations.getRetweetCount();
        CompletableFuture<Void> reTweetResult = averageRetweetCont.thenAccept(value -> value.forEach(s -> System.out.println(
                "@" + s.getText() + " \n*********RETWEETCOUNT*******\n" + s.getRetweetCount() + "\n")));

        System.out.println("\n\n\n\n\n\n\n\n\n");
        CompletableFuture<List<Status>> averageFavouriteCont = twitterOperations.getFavouriteCount();
        CompletableFuture<Void> reFavouriteResult = averageFavouriteCont.thenAccept(value -> value.forEach(s -> System.out.println(
                "@" + s.getText() + "\n*********LIKES************* " + s.getFavoriteCount() + "\n")));


        try {
            Thread.sleep(1000000);
        } catch (java.lang.InterruptedException ex) {
            System.out.print(ex.getMessage());
        }

    }
}
