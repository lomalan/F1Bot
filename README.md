### Steps to deploy applications
1. Install ngrok locally on your machine
2. Execute this snipped of code from ngrok directory:
   ngrok http 9999
3. Put created tunneling address in the bot.webhookUrl property 
4. mvn clean package docker:build
5. docker-compose up f1bot from /src/main/docker/local directory
