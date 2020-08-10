import router from "@/router";
import { EVENT_TYPE } from "@/utils/constants";

export const POST_OVERLAY_TEMPLATES = (post) => {
  const CONTENT_LENGTH = 15;

  const postOverlay = document.createElement("div");
  postOverlay.classList.add("speech-bubble");

  const shortContent = post.content.substring(0, CONTENT_LENGTH).trim();
  const contentTail = post.content.length > CONTENT_LENGTH ? "..." : "";
  postOverlay.innerText = shortContent + contentTail;

  postOverlay.insertAdjacentHTML(
    "beforeend",
    `<span class="red--text text-caption"> [${post.comments.length}]</span>`,
  );

  postOverlay.insertAdjacentHTML(
    "beforeend",
    `<div class="speech-arrow"></div>`,
  );

  const routePage = () => router.push("posts/" + post.id);
  postOverlay.addEventListener(EVENT_TYPE.CLICK, routePage);

  return postOverlay;
};
