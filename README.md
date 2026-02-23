# Spring AI Apps Project [![Twitter](https://img.shields.io/twitter/follow/piotr_minkowski.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/piotr_minkowski)

[![CircleCI](https://circleci.com/gh/piomin/spring-ai-showcase.svg?style=svg)](https://circleci.com/gh/piomin/spring-ai-showcase)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=piomin_spring-ai-showcase)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=piomin_spring-ai-showcase&metric=bugs)](https://sonarcloud.io/dashboard?id=piomin_spring-ai-showcase)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=piomin_spring-ai-showcase&metric=coverage)](https://sonarcloud.io/dashboard?id=piomin_spring-ai-showcase)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=piomin_spring-ai-showcase&metric=ncloc)](https://sonarcloud.io/dashboard?id=piomin_spring-ai-showcase)

This project demonstrates the integration of AI capabilities within a Spring Boot application, utilizing the [Spring AI](https://github.com/spring-projects/spring-ai) framework.

-----

## Table of Contents

- [Architecture](#architecture)
- [Running the Application](#running-the-application)
- [Articles](#articles)

## Running the application

You can always run all the apps locally with Spring Maven Plugin support:
```shell
mvn spring-boot:run
```

Sometimes, you would have to connect the app with the AI model. It can be OpenAI or your preferred model. Before running the app you should export the API token as shown below.
```shell
export SPRING_AI_OPENAI_API_KEY=<YOUR_OPENAI_API_KEY>
```

## Architecture

### MCP Client/Server

The example is available in the `spring-ai-mcp` directory. Here's the diagram that visualizes architecture.

<img src="https://i0.wp.com/piotrminkowski.com/wp-content/uploads/2025/03/Screenshot-2025-03-16-at-12.24.46.png?w=1392&ssl=1" title="Architecture"><br/>

# Articles
1. Getting started with the Spring AI **MCP** concept. Implement a client-side and a server-side application that exposes `@Tools` and `Prompts` to other services. The example is available in the branch [master](https://github.com/piomin/spring-ai-showcase/tree/master). A detailed guide may be found in the following article: [Using Model Context Protocol (MCP) with Spring AI](https://piotrminkowski.com/2025/03/17/using-model-context-protocol-mcp-with-spring-ai/)
2. Getting started with the Spring AI **MCP** concept. Implement a client-side that connects with external MCP servers. The example is available in the branch [master](https://github.com/piomin/spring-ai-showcase/tree/master). A detailed guide may be found in the following article: [Spring AI with External MCP Servers](https://piotrminkowski.com/2026/02/06/spring-ai-with-external-mcp-servers/)
