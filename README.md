### Steps to deploy application(non docker way)
1. Install ngrok, mongodb, kafka locally on your machine
2. Execute code below from ngrok directory:
   ngrok http 9999
3. Put created tunneling address in the bot.webhookUrl property 
4. Just run the application using F1BotApplication class

### Steps to deploy application(Docker way)
1. Change placeholder values and run /docker/docker-compose.yml. 
There is an information below on how to get data. 

For enable possibility to get live data or collect tweets, please download repos: 
1. https://github.com/lomalan/F1LiveTiming
2. https://github.com/lomalan/TweetCollector



####Other notes:
To use weather service you need to create profile on https://api.openweathermap.org

How to create bot you can read here  
https://core.telegram.org/bots

When bot created you will have personal token which you can use in the application properties
