# 企业级医疗智能助手全栈项目 - 硅谷小智（医疗版）

## 🏥 项目概述

本项目是一个完整的、企业级医疗智能助手原型系统，名为“硅谷小智（医疗版）”。它集成了大模型微调、智能体（Agent）开发、检索增强生成（RAG）、企业级私有化部署与前端交互，形成了一个从数据到应用的技术闭环。项目旨在验证在数据安全与可控要求下，构建具备专业领域知识、业务处理能力和良好交互体验的AI助手的可行性。

**核心价值**：提供了一套可复用的方法论和工程实践，涵盖从模型优化、应用开发到安全部署的全链路，适用于医疗、金融、法律等对数据隐私和领域专业性要求高的场景。

## 🚀 项目架构与核心模块

本项目由三个核心仓库组成，采用模块化设计：

1. **本仓库（主项目）**：`[请替换为您的仓库地址]`
   - **核心**：基于SpringBoot + LangChain4j的智能体（Agent）应用系统。
   - **功能**：AI分导诊、模拟挂号预约/取消、医疗咨询、对话记忆、RAG知识库问答、流式输出。
   - **技术栈**：Java, SpringBoot, LangChain4j, MySQL, MongoDB, Pinecone, Docker。
2. **大模型微调子项目**：`git@github.com:yangyue68/medical-qa-lora-qlora.git`
   - **核心**：基于Qwen2.5-3B-Instruct模型的中文医疗问答LoRA/QLoRA微调实验。
   - **功能**：研究训练数据规模（10k/20k/40k/60k）对模型在医疗领域表现的影响，为智能体提供优化的底座模型。
   - **技术栈**：Python, PyTorch, PEFT, Transformers, LoRA/QLoRA。
3. **前端交互工程**：`[如果前端代码独立存放，请说明地址]`
   - **核心**：智能助手交互界面。
   - **功能**：会话管理、快捷提问、流式消息展示、模拟挂号操作。
   - **技术栈**：HTML, CSS, JavaScript (Vue.js/React风格)。

## 📁 主项目目录结构

```
enterprise-medical-assistant/
├── README.md                           # 本文件
├── pom.xml                             # Maven项目依赖管理
├── src/
│   └── main/
│       ├── java/com/atguigu/xiaozhi/   # 主Java源码包
│       │   ├── agent/                  # 智能体核心配置与接口 (XiaozhiAgent)
│       │   ├── config/                 # Spring配置类 (数据库、模型、向量存储等)
│       │   ├── controller/             # REST API控制器 (XiaozhiController)
│       │   ├── entity/                 # 实体类 (Appointment, ChatMessage等)
│       │   ├── mapper/                 # MyBatis Mapper接口与XML
│       │   ├── service/                # 业务服务层
│       │   ├── tool/                   # Function Calling工具类 (预约、查询等)
│       │   └── Application.java        # SpringBoot启动类
│       └── resources/
│           ├── application.properties  # 应用配置文件
│           ├── mapper/                 # MyBatis XML映射文件
│           ├── prompts/                # 提示词模板文件
│           └── knowledge_base/         # RAG知识库文档 (PDF, TXT等)
├── docs/                               # 项目文档、部署说明
└── frontend/                           # 前端工程代码 (可选，可独立仓库)
```

## 🛠️ 核心技术栈与特性

| 模块           | 技术/组件                | 说明                                                         |
| :------------- | :----------------------- | :----------------------------------------------------------- |
| **智能体框架** | LangChain4j + AIService  | 使用`@AiService`动态代理构建智能体，实现输入/输出自动转换、组件组装。 |
| **核心功能**   | Function Calling (Tools) | 定义`@Tool`方法，实现预约挂号、取消预约、号源查询等业务工具调用。 |
| **对话记忆**   | ChatMemory + MongoDB     | 实现多轮对话记忆，并通过MongoDB进行持久化存储，支持记忆隔离（按用户/会话）。 |
| **知识增强**   | RAG (检索增强生成)       | 集成Pinecone向量数据库，使用BGE等嵌入模型，实现基于专业医疗知识库的精准问答。 |
| **模型接入**   | 多模型支持               | 支持OpenAI GPT、DeepSeek、通义千问/万象、Ollama本地模型等多种大模型接入。 |
| **流式输出**   | Streaming Chat Model     | 配置流式大模型，实现答案的逐字返回，优化用户体验。           |
| **数据持久化** | MySQL + MongoDB          | MySQL存储业务数据（预约记录），MongoDB存储聊天记忆。         |
| **私有化部署** | Docker + 多服务器架构    | 使用Docker部署Dify（可选），并通过Ollama、Xinference部署私有模型与嵌入服务，实现完全内网环境运行。 |
| **前端交互**   | HTML/CSS/JS              | 提供简洁美观的聊天界面，支持会话管理、消息流式展示。         |

## ⚙️ 快速开始

### 环境要求

- **JDK**: 17+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **MongoDB**: 6.0+
- **Pinecone账户** (用于向量存储，可选，可使用InMemoryEmbeddingStore替代测试)
- **大模型API Key** (如OpenAI、DeepSeek、阿里百炼等，至少配置一种)

### 1. 克隆项目与配置

```
git clone [您的仓库地址]
cd enterprise-medical-assistant
```

### 2. 数据库初始化

1. 创建MySQL数据库（如 `medical_assistant`）。
2. 执行 `docs/sql/init_table.sql` 创建预约业务表。
3. 确保MongoDB服务运行。

### 3. 应用配置

编辑 `src/main/resources/application.properties`，配置以下关键项：

```
# 数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/medical_assistant
spring.datasource.username=root
spring.datasource.password=your_password

# MongoDB连接
spring.data.mongodb.uri=mongodb://localhost:27017/chat_memory_db

# 大模型配置 (以DeepSeek为例)
langchain4j.open-ai.chat-model.api-key=${DEEPSEEK_API_KEY}
langchain4j.open-ai.chat-model.base-url=https://api.deepseek.com
langchain4j.open-ai.chat-model.model-name=deepseek-chat

# Pinecone向量数据库配置 (可选)
langchain4j.pinecone.api-key=${PINECONE_API_KEY}
langchain4j.pinecone.environment=gcp-starter
langchain4j.pinecone.index-name=medical-knowledge
```

### 4. 构建与运行

```
mvn clean package
java -jar target/your-app.jar
```

应用启动后，默认访问地址：`http://localhost:8080`。前端界面可通过 `frontend/` 目录下的HTML文件访问，或独立部署。

## 🔗 关联项目使用说明

### 大模型微调项目 (`medical-qa-lora-qlora`)

本项目使用的底座模型可通过微调进行优化。微调实验代码与数据存放在独立仓库：

```
git clone git@github.com:yangyue68/medical-qa-lora-qlora.git
```

详细的环境准备、数据预处理、训练与评估步骤，请参考该仓库的 `README.md`。训练得到的适配器权重可集成到本项目的模型配置中，以提升医疗领域问答的专业性。

## 🧪 项目核心功能演示

1. **AI分导诊与医疗咨询**：用户描述症状，智能体推荐科室并提供初步医疗建议。

2. 模拟挂号全流程

   ：

   - **查询号源**：`“帮我查一下下周一下午内科有没有号？”`
   - **预约挂号**：`“我要预约下周一上午内科的张医生。”` (智能体会引导补全必要信息)
   - **取消预约**：`“取消我预约的周一上午的号。”`

3. **专业知识库问答 (RAG)**：基于上传的医院科室介绍、医生资料等文档，回答如`“心血管内科的李主任擅长哪些疾病？”`等问题。

4. **多轮对话与记忆**：对话中能记住用户提供的姓名、症状等信息，并在后续交互中引用。

## 🚢 企业级私有化部署方案

本项目验证了一套完整的企业级私有化部署架构，确保数据不出域：

1. **应用服务器**：部署本SpringBoot应用。
2. **模型服务器**：使用 **Ollama** 部署私有化的大语言模型（如Qwen）。
3. **嵌入模型服务器**：使用 **Xinference** 部署私有化的嵌入模型（如BGE）和重排序模型。
4. **向量数据库**：使用 **Pinecone** 云服务或本地部署的向量数据库（如ChromaDB）。
5. **通过Docker容器化**和**内网隧道**打通服务间网络，实现安全、可控的闭环部署。

详细部署步骤请参考 `docs/deployment_guide.md`。

## 📈 项目成果与验证

通过本项目，成功验证了：

- **模型微调有效性**：LoRA/QLoRA微调将Qwen2.5-3B模型在医疗术语识别上的性能显著提升。
- **智能体工程可行性**：基于LangChain4j的AIService能够稳定支撑包含复杂业务逻辑（Function Calling）和状态管理（记忆）的智能体。
- **RAG流程实用性**：结合向量数据库，能够有效利用专业领域知识库，增强回答的准确性和时效性。
- **私有化部署成熟度**：多组件分离部署方案可行，满足企业数据安全要求。

## 📄 许可证

本项目仅供学习与技术交流使用。

## 🤝 贡献与联系

欢迎提交Issue和Pull Request。如有任何问题，可通过项目Issue进行讨论。

------

**开始探索**：按照快速开始步骤部署项目，体验从模型微调到智能体应用的全流程。结合微调子项目，您可以进一步优化底座模型，打造更专业的领域智能助手。