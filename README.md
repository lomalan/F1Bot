### Steps to deploy application
1. Install ngrok locally on your machine
2. Execute code below from ngrok directory:
   ngrok http 9999
3. Put created tunneling address in the bot.webhookUrl property 
4. mvn clean package docker:build
5. docker-compose up f1bot from src/main/docker/local directory

For enable possibility to get live data please download repo: https://github.com/lomalan/F1LiveTiming


####Other notes:
To use weather service you need to create profile on https://api.openweathermap.org

How to create bot you can read here  
https://core.telegram.org/bots

When bot will be created you will have personal token which you can use in application properties
