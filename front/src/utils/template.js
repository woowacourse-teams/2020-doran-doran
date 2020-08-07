import router from "@/router";
import { EVENT_TYPE } from "@/utils/constants";

const POST_OVERLAY_CONTENT_LENGTH = 20;

export const POST_OVERLAY_TEMPLATES = (post) => {
  const postOverlay = document.createElement("div");
  postOverlay.classList.add("speech-bubble");

  const shortContent = post.content
    .substring(0, POST_OVERLAY_CONTENT_LENGTH)
    .trim();
  const contentTail = post.content.length > 20 ? "..." : "";
  postOverlay.innerText = shortContent + contentTail;

  postOverlay.insertAdjacentHTML(
    "beforeend",
    `<span class="red--text"> [${post.comments.length}]</span>`,
  );

  postOverlay.insertAdjacentHTML(
    "beforeend",
    `<div class="speech-arrow"></div>`,
  );

  const routePage = () => router.push("posts/" + post.id);
  postOverlay.addEventListener(EVENT_TYPE.CLICK, routePage);

  return postOverlay;
};
