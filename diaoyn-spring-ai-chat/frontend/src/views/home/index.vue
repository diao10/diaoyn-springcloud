<template>
  <div class="__container_home_index">
    <a-row class="row" :gutter="[10, 10]">
      <a-col :span="8">
      </a-col>
      <a-col :span="16">
        <a-card class="card chat">
          <template #title>
            <label style="font-size: 25px">Diaoyn Test</label>
          </template>
          <div class="flex-grow">
            <a-card class="chat-body">
              <MessageList :list="messageInfo.list"></MessageList>
              <div
                id="chat-body-id"
                style="height: 5px; margin-top: 20px"
              ></div>
            </a-card>
          </div>
          <a-row class="footer" :gutter="10">
            <a-col :span="20">
              <a-input
                @keydown.enter="forHelp"
                v-model:value="question"
                placeholder="Message"
              ></a-input>
            </a-col>
            <a-col :span="4">
              <a-button @click="forHelp" :disabled="lock" type="primary"
                >发送</a-button
              >
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { PRIMARY_COLOR } from "@/base/constants";
import { nextTick, onMounted, reactive, ref } from "vue";
import MessageList from "@/views/home/MessageList.vue";
import type { MessageItem } from "@/types/message";
import { v4 as uuidv4 } from "uuid";
import { message } from "ant-design-vue";

const messageInfo: { cur: MessageItem | null; list: MessageItem[] } = reactive({
  cur: null,
  list: [
    {
      role: "assistant",
      content: "欢迎来到Diaoyn Test! 请问有什么可以帮您的?",
    },
  ],
});

const question = ref("");
let scrollItem: any = null;

function scrollBottom() {
  scrollItem?.scrollIntoView({ behavior: "smooth", block: "end" });
}

function addMessage(role: "user" | "assistant", content: string) {
  let cur = {
    role,
    content,
  };
  messageInfo.cur = cur;
  messageInfo.list.push(cur);
  nextTick(() => {
    scrollBottom();
  });
}

const lock = ref(false);
function appendMessage(content: string) {
  if (messageInfo.cur) {
    messageInfo.cur.content += content;
  }
  scrollBottom();
}

const chatId = uuidv4();

function forHelp() {
  if (lock.value) {
    message.warn("助手正在生成, 请耐心等候");
    return;
  }
  let userMessage = question.value;
  addMessage("user", userMessage);
  question.value = "";
  const eventSource = new EventSource(
    `/api/model/stream/chat?chatId=${chatId}&message=${userMessage}`,
    {},
  );
  eventSource.onopen = function (event) {
    addMessage("assistant", "");
  };
  eventSource.onmessage = function (event) {
    lock.value = true;
    appendMessage(event.data);
  };
  eventSource.onerror = function () {
    eventSource.close();
    lock.value = false;
  };
}


let __null = PRIMARY_COLOR;
onMounted(() => {
  scrollItem = document.getElementById("chat-body-id");
});
</script>
<style lang="less" scoped>
.__container_home_index {
  height: 100vh;
  max-height: 100vh;
  overflow: auto;
  padding-top: 2px;

  .row {
    height: 100%;
  }

  .card {
    height: 100%;
  }

  :deep(.ant-card-body) {
    height: calc(100vh - 180px);
    display: flex;
    flex-direction: column;
    padding: 5px;
    border-radius: 0;

    .chat-body {
      border: none;
      height: calc(100% - 80px);
      overflow: auto;
      background: #f4f5f7;
    }
  }

  .flex-grow {
    flex-grow: 1; /* 让其他元素占据剩余空间 */
  }

  .footer {
    width: 100%;
  }
}
</style>
