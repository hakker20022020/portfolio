package uz.inha.portfolio.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.inha.portfolio.model.Attachment;
import uz.inha.portfolio.model.AttachmentContent;
import uz.inha.portfolio.repository.AttachmentContentRepository;
import uz.inha.portfolio.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("uz/portfolio")
@AllArgsConstructor
public class RegisterController {


    private AttachmentRepository attachmentRepository;

    private AttachmentContentRepository attachmentContentRepository;

    @PostMapping("/register")
    public HttpEntity<?> register(){

    }

    @PostMapping("/upload")
    public String uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            Attachment attachment = new Attachment();
            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(size);
            attachment.setContentType(contentType);
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setMainContent(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);

            attachmentContentRepository.save(attachmentContent);
            return "File saved. Id is : " + savedAttachment.getId();
        }

        return "Error";
    }

    @GetMapping("/getFile/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);

        if (optionalAttachment.isPresent()) {
            Attachment attachment = new Attachment();
            Optional<AttachmentContentRepository> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            //Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()) {
                //AttachmentContent attachmentContent = contentOptional.get();

                AttachmentContentRepository attachmentContent = contentOptional.get();
                response.setHeader("Content-Disposition", "attachment: filename = \"" +
                        attachment.getFileOriginalName() + "\"");
                response.setContentType(attachment.getContentType());

                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
            }
        }
    }



}
