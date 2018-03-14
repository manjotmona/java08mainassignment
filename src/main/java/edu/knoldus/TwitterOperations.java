package edu.knoldus;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import static java.util.stream.Collectors.toList;

/**
 * Created by pallavi on 14/3/18.
 */
public class TwitterOperations {
    private static Twitter twitter = TwitterFactory.getSingleton();
    Query query = new Query("#sath");

    public CompletableFuture<List<Status>> getLatestPostFromTimeLine() {

        CompletableFuture<List<Status>> futureTwitter = CompletableFuture.supplyAsync(() -> {

            List<Status> collect = null;
            query.setCount(100);

            try {
                ResponseList<Status> userTimeline = twitter.getUserTimeline();
                collect = userTimeline.stream().limit(20).collect(toList());
            } catch (twitter4j.TwitterException ex) {
                System.out.print(ex.getErrorMessage());
            }
            return collect;

        });
        return futureTwitter;

    }

    public CompletableFuture<List<Status>> getOldestPostFromTimeline() {

        CompletableFuture<List<Status>> futureTwitter = CompletableFuture.supplyAsync(() -> {

            List<Status> collect = null;
            query.setCount(100);

            try {
                ResponseList<Status> userTimeline = twitter.getUserTimeline();
                collect = userTimeline.stream().limit(20).collect(toList());
                collect.sort(Comparator.comparingLong(x -> x.getCreatedAt().getTime()));

            } catch (twitter4j.TwitterException ex) {
                System.out.print(ex.getErrorMessage());
            }
            return collect;

        });

        //System.out.println("\n\n\n\n\n\nInside Function:");


        return futureTwitter;

    }

    public CompletableFuture<Long> gettweetsOnParticularDate() {

        CompletableFuture<Long> futureTweetsOnADay = CompletableFuture.supplyAsync(() -> {

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            String time = "Wed Jan 31 21:00:00 IST 2018";
          //  LocalDate newDate = LocalDate.of(2018,1,3);

            List<Status> tweets1 = null;
            query.setCount(100);
            long count = 0;

            try {
                Date date = dateFormat.parse(time);
                QueryResult result = twitter.search(query);
                tweets1 = result.getTweets();
                count = tweets1.stream().filter(tweet -> tweet.getCreatedAt() == date).count();

            } catch (twitter4j.TwitterException ex) {
                System.out.print(ex.getErrorMessage());
            } catch (java.text.ParseException p) {
                System.out.print(p.getMessage());
            }
            return count;

        });
        return futureTweetsOnADay;
    }

    public CompletableFuture<List<Status>> getRetweetCount() {

        CompletableFuture<List<Status>> futureRetweetList = CompletableFuture.supplyAsync(() -> {

            List<Status> tweets = null;
            query.setCount(100);

            try {

                QueryResult result = twitter.search(query);
                tweets = result.getTweets();

                tweets.sort(Comparator.comparing(Status::getRetweetCount).reversed());
            } catch (twitter4j.TwitterException ex) {
                System.out.print(ex.getMessage());
            }

            return tweets;

        });
        return futureRetweetList;
    }

    public CompletableFuture<List<Status>> getFavouriteCount() {

        CompletableFuture<List<Status>> futurefavouriteTweet = CompletableFuture.supplyAsync(() -> {

            List<Status> tweets1 = null;
            query.setCount(99);

            try {

                QueryResult result = twitter.search(query);
                tweets1 = result.getTweets();
                tweets1.sort(Comparator.comparing(Status::getFavoriteCount).reversed());

            } catch (twitter4j.TwitterException ex) {
                System.out.print(ex.getErrorMessage());
            }

            return tweets1;

        });
        return futurefavouriteTweet;
    }

}
